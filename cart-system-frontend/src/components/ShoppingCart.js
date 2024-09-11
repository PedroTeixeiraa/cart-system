import React, { useState, useEffect } from 'react';
import { Grid, Container, Card, CardContent, Typography, Button, List, ListItem, ListItemText, TextField } from '@mui/material';
import axios from 'axios';

const ShoppingCart = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [totalSavings, setTotalSavings] = useState(0);
  const [promoCode, setPromoCode] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8082/api/products')
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error('Error fetching products:', error);
      });
  }, []);

  const handleAddToCart = (productId) => {
    axios.post(`http://localhost:8082/api/cart/add`, {
        productId, 
        quantity: 1, 
    })
      .then((response) => {
        setCart(response.data.items);
        setTotalPrice(response.data.totalPrice);
      })
      .catch((error) => {
        console.error('Error adding to cart:', error);
      });
  };

  const handleApplyPromoCode = () => {
    axios.post(`http://localhost:8082/api/cart/apply-promotion`, null, { params: { promotionCode: promoCode } })
      .then((response) => {
        setCart(response.data.items);
				setTotalPrice(response.data.totalPrice);
        setTotalSavings(response.data.totalSavings);
      })
      .catch((error) => {
        console.error('Error applying promo code:', error);
      });
  };

  return (
    <Container>
      <Grid container spacing={2}>
        <Grid item xs={7}>
          <Typography variant="h5" gutterBottom>
            Products
          </Typography>
          <Grid container spacing={2}>
            {products.map((product) => (
              <Grid item xs={10} sm={6} md={4} key={product.id}>
                <Card>
                  <CardContent>
                    <Typography variant="h6">{product.name}</Typography>
                    <Typography variant="body2">Price: {product.price / 100} USD</Typography>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => handleAddToCart(product.id)}
                    >
                      Add to Cart
                    </Button>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Grid>

        <Grid item xs={4}>
          <Typography variant="h5" gutterBottom>
            Shopping Cart
          </Typography>
          <TextField
            label="Discount Code"
            value={promoCode}
            onChange={(e) => setPromoCode(e.target.value)}
            variant="outlined"
            fullWidth
            margin="normal"
        />
            <Button
            variant="contained"
            color="secondary"
            onClick={handleApplyPromoCode}
            fullWidth
            >
            Apply Promo Code
            </Button>

          <List>
            {cart.map((item) => (
              <ListItem key={item.product.id}>
                <ListItemText
                  primary={item.product.name}
                  secondary={`Quantity: ${item.quantity} | Price: ${(item.product.price * item.quantity) / 100} USD`}
                />
              </ListItem>
            ))}
            <Typography variant="h6">
                Total Price: {(totalPrice / 100).toFixed(2)} USD
            </Typography>
            <Typography variant="h6">
                Total Savings: {(totalSavings / 100).toFixed(2)} USD
            </Typography>
          </List>
          
        </Grid>
      </Grid>
    </Container>
  );
};

export default ShoppingCart;