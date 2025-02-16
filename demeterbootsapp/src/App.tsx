import React from 'react';
import Button from './components/button';
import Checkbox from './components/checkbox';
import Table from './components/table';
import TextArea from './components/textarea';
import TextField from './components/textfield';
import "./styling/components/components.scss";

import './App.css';

function App() {
  return (
    <div className="App">
      <Button variant="primary">Click me!</Button>
      <Checkbox label="I agree to the terms and conditions" />
      <Table headers={["Name", "Age", "Email"]} data={[{ Name: "John Doe", Age: 25, Email: ""}, { Name: "Jane Doe", Age: 36, Email: "test@email"}]}/>
      <TextArea label="Comments" rows={5} />
      <TextField label="Name" placeholder='Enter name'/>
    </div>
  );
}

export default App;
