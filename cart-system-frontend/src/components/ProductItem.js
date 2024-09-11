import React from 'react';

const ProductItem = ({ product }) => (
  <li>
    <h2>{product.name}</h2>
    <p>Price: ${product.price / 100}</p>
    <button>Add to Cart</button>
  </li>
);

export default ProductItem;