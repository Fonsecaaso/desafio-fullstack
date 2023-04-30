import React, { useState, useEffect } from "react";
import axios from "axios";
import { Navigate } from "react-router-dom";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from '@material-ui/core';


function Transactions() {
  const [transactions, setTransactions] = useState([]);
  const [searchValue, setSearchValue] = useState("");  
  const userToken = localStorage.getItem("token");


  useEffect(() => {
    async function fetchData() {
    const response = await axios.get("http://localhost:8085/transactions");
    setTransactions(response.data);
    }
    fetchData();
  }, []);

  if (userToken == null) {
    return <Navigate to="/login" />;
  }
  

  const filteredTransactions = transactions.filter((transaction) => {
    return (
      transaction.sellerName.includes(searchValue) ||
      transaction.product.includes(searchValue)
    );
  });

  return (
    <>
    <TextField
    label="Pesquisar por produto ou vendedor"
    variant="outlined"
    fullWidth
    margin="normal"
    value={searchValue}
    onChange={(e) => setSearchValue(e.target.value)}
  />
    <TableContainer>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Tipo da transação</TableCell>
            <TableCell>Data</TableCell>
            <TableCell>Descrição do produto</TableCell>
            <TableCell>Valor da transação em centavos</TableCell>
            <TableCell>Vendedor</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
        {filteredTransactions.map((transaction) => (
              <TableRow key={transaction.id}>
                <TableCell>{transaction.type}</TableCell>
                <TableCell>{transaction.date}</TableCell>
                <TableCell>{transaction.product}</TableCell>
                <TableCell>{transaction.value}</TableCell>
                <TableCell>{transaction.sellerName}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
    </>
  );
}

export default Transactions;