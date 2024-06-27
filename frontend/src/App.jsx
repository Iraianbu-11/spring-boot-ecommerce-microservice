// Import necessary components and functions
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Customer from "./components/customer/Customer";
import Navbar from "./components/utils/Navbar";
import AddCustomer from "./components/customer/AddCustomer";
import UpdateCustomer from "./components/customer/UpdateCustomer";
import Orders from "./components/orders/Orders";
import Products from "./components/products/Products";

function App() {
  return (
    <>
      <Navbar />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Products />} />
          <Route path="/customer" element = {<Customer/>}/>
          <Route path="/customer">
            <Route path="add" element={<AddCustomer />} />
            <Route path="update" element={<UpdateCustomer />} />
          </Route>
          <Route path="/order" element={<Orders />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
