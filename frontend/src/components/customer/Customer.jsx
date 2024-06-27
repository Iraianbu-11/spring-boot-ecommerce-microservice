import { useEffect, useState } from "react";
import { deleteCustomer, getCustomers } from "../../service/ecommerce";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
const Customer = () => {
  const [customers, setCustomers] = useState([]);
  const [customerDeleteId, setCustomerDeleteId] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    getCustomers()
      .then((res) => {
        setCustomers(res.data);
      })
      .catch((e) => {
        console.error(e)
        toast.error("Unable to fetch the Customers");
  });
  }, []);

  const handleChangeDelete = (e) => {
    setCustomerDeleteId(e.target.value);
  };

  const handleSubmitDelete = async (e) => {
    e.preventDefault();
    deleteCustomer(customerDeleteId)
      .then(() => {
        console.log("Deleted");
        toast.success("Deleted Successfully");
      })
      .catch((err) => console.log(err));
    setCustomerDeleteId("");
  };

  return (
    <>
      <div className="container mt-5">
        <h2 className="text-center">ZcommercE Customers</h2>
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Id</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Street</th>
              <th>House Number</th>
              <th>Zipcode</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.firstName}</td>
                <td>{p.lastName}</td>
                <td>{p.email}</td>
                <td>{p.address.street}</td>
                <td>{p.address.houseNumber}</td>
                <td>{p.address.zipcode}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <h4 className="text-center">Delete Customer</h4>
            <form onSubmit={handleSubmitDelete}>
              <div className="input-group mb-3">
                <span
                  className="input-group-text"
                  id="inputGroup-sizing-default"
                >
                  Customer Id
                </span>
                <input
                  type="text"
                  name="customerId"
                  className="form-control"
                  aria-label="Sizing example input"
                  aria-describedby="inputGroup-sizing-default"
                  value={customerDeleteId}
                  onChange={handleChangeDelete}
                />
              </div>

              <div className="d-flex justify-content-center">
                <button type="submit" className="btn btn-danger mx-auto">
                  Delete
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div className="container mt-5">
  <div className="row justify-content-center">
    <div className="col-md-6 d-flex justify-content-center">
      <button type="submit" className="btn btn-primary mx-2" onClick={() => navigate("/customer/add") }>
        Add
      </button>
      <button type="submit" className="btn btn-primary mx-2" onClick={() => navigate("/customer/update") }>
        Update
      </button>
    </div>
  </div>
</div>

      <ToastContainer />
    </>
  );
};

export default Customer;
