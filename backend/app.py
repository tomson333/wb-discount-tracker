from flask import Flask, jsonify, request
from flask_cors import CORS
import requests

app = Flask(__name__)
CORS(app)

# Wildberries catalog endpoint (public)
# We use a sample endpoint that returns product lists by query.
WB_CATALOG_URL = "https://catalog.wb.ru/catalog/quick-search"

def fetch_wb(query: str, page: int = 1, per_page: int = 20):
    params = {
        "query": query,
        "page": page,
        "limit": per_page
    }
    # The WB public endpoints sometimes require additional params; this is a lightweight example.
    resp = requests.get(WB_CATALOG_URL, params=params, timeout=10)
    resp.raise_for_status()
    data = resp.json()
    products = data.get("data", {}).get("products", []) if isinstance(data, dict) else []
    results = []
    for p in products:
        # fields may vary; adapt if needed
        pid = p.get("id")
        name = p.get("name") or p.get("title") or ""
        price_old = p.get("salePriceU", 0) / 100.0 if p.get("salePriceU") else None
        price_new = p.get("priceU", 0) / 100.0 if p.get("priceU") else None
        discount = None
        try:
            if price_old and price_new and price_old > 0:
                discount = int(round((price_old - price_new) / price_old * 100))
        except:
            discount = None
        url = f"https://www.wildberries.ru/catalog/{pid}/detail.aspx" if pid else ""
        results.append({
            "id": str(pid),
            "title": name,
            "old_price": f"{price_old:.2f} ₽" if price_old else None,
            "new_price": f"{price_new:.2f} ₽" if price_new else None,
            "discount": f"{discount}%" if discount is not None else None,
            "url": url,
            "source": "wildberries"
        })
    return results

@app.route('/api/discounts')
def discounts():
    q = request.args.get("q", "смартфон")
    page = int(request.args.get("page", "1"))
    per_page = int(request.args.get("per_page", "20"))
    try:
        items = fetch_wb(q, page=page, per_page=per_page)
        if items:
            return jsonify(items)
    except Exception as e:
        app.logger.error("WB fetch error: %s", str(e))
    # fallback sample
    sample = [
        {"id":"1","title":"Sample Phone A","old_price":"30000.00 ₽","new_price":"25000.00 ₽","discount":"17%","url":"https://www.wildberries.ru/","source":"sample"},
        {"id":"2","title":"Sample Phone B","old_price":"20000.00 ₽","new_price":"15000.00 ₽","discount":"25%","url":"https://www.wildberries.ru/","source":"sample"}
    ]
    return jsonify(sample)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
