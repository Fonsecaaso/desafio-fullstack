import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  TextField,
  Grid,
} from "@material-ui/core";


function UserList() {
  const [users, setUsers] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  
  useEffect(() => {
    async function fetchData() {
      const response = await axios.get("http://0.0.0.0:8087/users");
      setUsers(response.data);
    }
    fetchData();
  }, []);


  const filteredUsers = users.filter((user) =>
    user.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const produtor = filteredUsers.filter((user) => user.produtor === true);
  const afiliado = filteredUsers.filter((user) => user.produtor === false);

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <TextField
          label="Buscar por nome"
          variant="outlined"
          fullWidth
          margin="normal"
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
        />
      </Grid>
      <Grid item xs={12}>
        <Typography variant="h5" align="center" gutterBottom>
          Usuários Produtores
        </Typography>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nome</TableCell>
                <TableCell>Saldo em Conta</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {produtor.map((user) => (
                <TableRow key={user.id}>
                  <TableCell>{user.nome}</TableCell>
                  <TableCell>{user.saldo}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Grid>
      <Grid item xs={12}>
        <Typography variant="h5" align="center" gutterBottom>
          Usuários Afiliados
        </Typography>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nome</TableCell>
                <TableCell>Saldo em Conta</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {afiliado.map((user) => (
                <TableRow key={user.id}>
                  <TableCell>{user.nome}</TableCell>
                  <TableCell>{user.saldo}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Grid>
    </Grid>
  );
}

export default UserList;
