import { useState } from 'react';
import { addCustomer , isCustomerPresent } from '../../service/ecommerce';
import { useNavigate } from 'react-router-dom';
import { toast , ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
const AddCustomer = () => {
  const navigate = useNavigate();
  const [customerId, setCustomerId] = useState("");
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    address: {
      street: '',
      houseNumber: '',
      zipcode: ''
    }
  });
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name in formData.address) {
      setFormData((prevData) => ({
        ...prevData,
        address: {
          ...prevData.address,
          [name]: value
        }
      }));
    } else {
      setFormData((prevData) => ({
        ...prevData,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const jsonData = JSON.stringify(formData);
    console.log(jsonData);
    addCustomer(jsonData)
        .then((response) => {
            console.log(response.id);
            toast.success("Customer Added Successfully!!!", {
                onClose: () => navigate("/customer/home")
            });
        })
        .catch(err => {
            console.log(err);
            toast.error("Failed to add Customer!!!");
        });
};

const handleChangeCustomer = (e) => {
  setCustomerId(e.target.value);
};

const handleSubmitCustomer = async (e) => {
  e.preventDefault();
  isCustomerPresent(customerId).then(res => {
      console.log(res);
      if(res.data === true){
        toast.success("Customer Exists");
      }
      else{
        toast.error("Customer Not Found");
      }
      setCustomerId("");
  })

};


  return (
    <>
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6 mt-3">
            <h1 className="text-center">Add Customer Details</h1>
            <br />
            <form onSubmit={handleSubmit}>
              <div className="input-group mb-3">
                <span className="input-group-text">First and Last Name</span>
                <input
                  type="text"
                  name="firstName"
                  aria-label="First name"
                  className="form-control"
                  value={formData.firstName}
                  onChange={handleChange}
                />
                <input
                  type="text"
                  name="lastName"
                  aria-label="Last name"
                  className="form-control"
                  value={formData.lastName}
                  onChange={handleChange}
                />
              </div>

              <div className="input-group mb-3">
                <span className="input-group-text" id="inputGroup-sizing-default">Email</span>
                <input
                  type="text"
                  name="email"
                  className="form-control"
                  aria-label="Sizing example input"
                  aria-describedby="inputGroup-sizing-default"
                  value={formData.email}
                  onChange={handleChange}
                />
              </div>

              <div className="input-group mb-3">
                <span className="input-group-text">Address</span>
                <input
                  type="text"
                  name="street"
                  aria-label="Street Name"
                  placeholder="Street Name"
                  className="form-control"
                  value={formData.address.street}
                  onChange={handleChange}
                />
                <input
                  type="text"
                  name="houseNumber"
                  aria-label="House Number"
                  placeholder="House Number"
                  className="form-control"
                  value={formData.address.houseNumber}
                  onChange={handleChange}
                />
                <input
                  type="text"
                  name="zipcode"
                  aria-label="Zipcode"
                  placeholder="Zipcode"
                  className="form-control"
                  value={formData.address.zipcode}
                  onChange={handleChange}
                />
              </div>
             
              <div className="d-flex justify-content-center">
                <button type="submit" className="btn btn-primary mx-auto">Add</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <h4 className="text-center">Check Customer Exists</h4>
            <form onSubmit={handleSubmitCustomer}>
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
                  value={customerId}
                  onChange={handleChangeCustomer}
                />
              </div>

              <div className="d-flex justify-content-center">
                <button type="submit" className="btn btn-primary mx-auto">Check</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <ToastContainer/>
    </>
  );
}

export default AddCustomer;
