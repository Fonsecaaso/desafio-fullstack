import React, { useState, useEffect } from "react";
import axios from "axios";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from '@material-ui/core';


function Transactions() {
  const [transactions, setTransactions] = useState([]);
  const [searchValue, setSearchValue] = useState("");
  
  useEffect(() => {
    async function fetchData() {
    const response = await axios.get("http://0.0.0.0:8087/transactions");
    setTransactions(response.data);
    }
    fetchData();
  }, []);

  const filteredTransactions = transactions.filter((transaction) => {
    return (
      transaction.vendedor.includes(searchValue) ||
      transaction.produto.includes(searchValue)
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
                <TableCell>{transaction.tipo}</TableCell>
                <TableCell>{transaction.data}</TableCell>
                <TableCell>{transaction.produto}</TableCell>
                <TableCell>{transaction.valor}</TableCell>
                <TableCell>{transaction.vendedor}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
    </>
  );
}

export default Transactions;