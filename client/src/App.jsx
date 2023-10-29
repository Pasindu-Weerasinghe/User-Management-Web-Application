import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

const App = () => {
  const [users, setUsers] = useState([]);
  const [userId, setUserId] = useState("");
  const [userName, setUserName] = useState("");
  const [userAddress, setUserAddress] = useState("");
  const [userMNumber, setUserMNumber] = useState("");
  const [highlightedRow, setHighlightedRow] = useState(null);
  

  

  const handleSave = async () => {
    const userData = {
      userId,
      userName,
      userAddress,
      userMNumber,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/user/saveUser",
        userData
      );
      console.log("User saved:", response.data);
      setUsers([...users, response.data]);
    } catch (error) {
      console.error("Error saving user:", error);
    }
  };

  const handleUpdate = async () => {
    const userData = {
      userId,
      userName,
      userAddress,
      userMNumber,
    };

    try {
      const response = await axios.put(
        "http://localhost:8080/api/v1/user/updateUser",
        userData
      );
      console.log("User updated:", response.data);
      setUsers(
        users.map((user) => (user.userId === userId ? response.data : user))
      );
    } catch (error) {
      console.error("Error updating user:", error);
    }
  };

  const handleDelete = async () => {
    try {
      await axios.delete("http://localhost:8080/api/v1/user/deleteUser/"+userId);
      console.log("User deleted:", userId);
      setUsers(users.filter((user) => user.userId !== userId));
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };
 

  const handleRowClick = (user) => {
    setHighlightedRow(user.userId);
    setUserId(user.userId);
    setUserName(user.userName);
    setUserAddress(user.userAddress);
    setUserMNumber(user.userMNumber);
  };
  useEffect(() => {
    async function fetchUsers() {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/user/getAllUsers"
        );
        setUsers(response.data.content);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    }

    fetchUsers();
  }, []);

  return (
    <div>
      <h1>Pasindu</h1>
      <div>
      
      <label htmlFor="userId">User ID</label>
      <br />
      <input
        type="text"
        id="userId"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        required
        disabled
      />
      <br />
      <label htmlFor="userName">User Name</label>
      <br />
      <input
        type="text"
        id="userName"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
        required
      />
      <br />
      <label htmlFor="userAddress">User Address</label>
      <br />
      <input
        type="text"
        id="userAddress"
        value={userAddress}
        onChange={(e) => setUserAddress(e.target.value)}
        required
      />
      <br />
      <label htmlFor="userMobileNumber">User Mobile Number</label>
      <br />
      <input
        type="text"
        id="userMobileNumber"
        value={userMNumber}
        onChange={(e) => setUserMNumber(e.target.value)}
        required
      />
      <br />
      <button onClick={handleSave}>Save User</button>
      <button onClick={handleUpdate}>Update User</button>
      <button onClick={handleDelete}>Delete User</button>
      
      </div>
      {users.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>User ID</th>
              <th>User Name</th>
              <th>User Address</th>
              <th>User Mobile Number</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user,index) => (
              <tr key={index} onClick={() => handleRowClick(user)}
              style={{ cursor: "pointer" }}
              className={highlightedRow === user.userId ? "highlighted" : ""}>
                <td>{user.userId}</td>
                <td>{user.userName}</td>
                <td>{user.userAddress}</td>
                <td>{user.userMNumber}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>Loading...</p> // You can display a loading message or another placeholder
      )}
    </div>
  );
};

export default App;
