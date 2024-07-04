import { useEffect, useState } from "react";
import { listOrders, placeOrder } from "../../service/ecommerce";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [orderData, setOrderData] = useState({
    reference: "",
    amount: 0,
    paymentMethod: "",
    customerId: "",
    products: [
      {
        productId: "",
        quantity: ""
      }
    ]
  });

  const referenceGenerator = () => {
  let now = new Date();
  let hours = String(now.getHours()).padStart(2, '0');
  let minutes = String(now.getMinutes()).padStart(2, '0');
  let date = String(now.getDate()).padStart(2, '0');
  let month = String(now.getMonth() + 1).padStart(2, '0'); 
  let year = String(now.getFullYear()).slice(-2);
  return `${hours}${minutes}${date}${month}${year}`;
}

  useEffect(() => {
    listOrders()
      .then((res) => {
        setOrders(res.data);
      })
      .catch((e) => {
        console.error(e)
        toast.error("Unable to fetch the Orders");
  });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setOrderData((prevData) => ({
      ...prevData,
      [name]: value,
      reference: "ZCoM-2024" + referenceGenerator()
    }));
  };

  const handleProductChange = (index, e) => {
    const { name, value } = e.target;
    const updatedProducts = [...orderData.products];
    updatedProducts[index][name] = value;
    setOrderData((prevData) => ({
      ...prevData,
      products: updatedProducts
    }));
  };

  const handleAddProduct = () => {
    setOrderData((prevData) => ({
      ...prevData,
      products: [...prevData.products, { productId: "", quantity: "" }]
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const jsonData = JSON.stringify(orderData);
    console.log(jsonData);
    placeOrder(jsonData)
      .then((response) => {
        console.log(response);
        toast.success("Order placed successfully✅✅\n Email Sent to your Account✉️✉️");
        setOrderData({
          reference: "",
          amount: 0,
          paymentMethod: "",
          customerId: "",
          products: [
            {
              productId: "",
              quantity: ""
            }
          ]
        });
      })
      .catch((err) => {
        console.error(err);
        toast.error("Failed to place order❌❌❌");
      });
  };

  const handleEmailClick = () => {
    window.open("http://localhost:1080", "_blank");
  };

  return (
    <>
      <div className="container mt-5">
        <h2 className="text-center">Orders List</h2>
        <table className="table table-striped table-bordered mt-3">
          <thead>
            <tr>
              <th>Id</th>
              <th>Reference</th>
              <th>Amount</th>
              <th>Payment Method</th>
              <th>Customer Id</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id}>
                <td>{order.id}</td>
                <td>{order.reference}</td>
                <td>{order.amount}</td>
                <td>{order.paymentMethod}</td>
                <td>{order.customerId}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6 mt-3">
            <h2 className="text-center">Place Order</h2>
            <br />
            <form onSubmit={handleSubmit}>
              <div className="input-group mb-3">
                <span className="input-group-text">Amount</span>
                <input
                  type="number"
                  name="amount"
                  className="form-control"
                  value={orderData.amount}
                  onChange={handleChange}
                />
              </div>

              <div className="input-group mb-3">
                <span className="input-group-text">Payment Method</span>
                <select
                  name="paymentMethod"
                  className="form-select"
                  value={orderData.paymentMethod}
                  onChange={handleChange}
                >
                  <option value="">Select Payment Method</option>
                  <option value="PAYPAL">PayPal</option>
                  <option value="CREDIT_CARD">Credit Card</option>
                  <option value="DEBIT_CARD">Debit Card</option>
                  <option value="GOOGLE_PAY">Google Pay</option>
                  <option value="BANK_TRANSFER">Bitcoin</option>
                </select>
              </div>

              <div className="input-group mb-3">
                <span className="input-group-text">Customer ID</span>
                <input
                  type="text"
                  name="customerId"
                  className="form-control"
                  value={orderData.customerId}
                  onChange={handleChange}
                />
              </div>

              <div className="input-group mb-3">
                {orderData.products.map((product, index) => (
                  <div key={index} className="mb-3">
                    <input
                      type="text"
                      name="productId"
                      placeholder="Product ID"
                      className="form-control"
                      value={product.productId}
                      onChange={(e) => handleProductChange(index, e)}
                    />
                    <input
                      type="number"
                      name="quantity"
                      placeholder="Quantity"
                      className="form-control mt-2"
                      value={product.quantity}
                      onChange={(e) => handleProductChange(index, e)}
                    />
                  </div>
                ))}
                <button
                  type="button"
                  className="btn btn-outline-primary"
                  onClick={handleAddProduct}
                >
                  Add Product
                </button>
              </div>

              <div className="d-flex justify-content-center">
                <button type="submit" className="btn btn-primary mx-2">
                  Place Order
                </button>
                <button
                  type="button"
                  className="btn btn-primary mx-2"
                  onClick={handleEmailClick}
                >
                  Email
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <ToastContainer />
    </>
  );
};

export default Orders;


