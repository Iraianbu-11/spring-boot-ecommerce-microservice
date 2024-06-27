import { useEffect, useState } from "react";
import { listProducts } from "../../service/ecommerce";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Products = () => {
    const [products, setProducts] = useState([]);
    useEffect(() => {
            listProducts()
            .then((res) => {
                setProducts(res.data);  
            })
            .catch((e) => {
                console.error(e)
                toast.error("Unable to fetch the Products")
    });
    }, []);

    return (
        <>
            <div className="container mt-3">
                <h2 className="text-center">Products List</h2>
                <table className="table table-striped table-bordered mt-3">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Available Quantity</th>
                            <th>Price</th>
                            <th>Category Id</th>
                            <th>Category Name</th>
                            <th>Category Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            products.map((p) => (
                                <tr key={p.id}>
                                    <td>{p.id}</td>
                                    <td>{p.name}</td>
                                    <td>{p.description}</td>
                                    <td>{p.availableQuantity}</td>
                                    <td>{p.price}</td>
                                    <td>{p.categoryId}</td>
                                    <td>{p.categoryName}</td>
                                    <td>{p.categoryDescription}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            <ToastContainer/>
        </>
    );
};

export default Products;
