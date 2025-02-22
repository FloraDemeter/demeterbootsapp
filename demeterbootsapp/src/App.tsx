import React from 'react';
import { useEffect}  from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import MainTemplate from './maintemplate';
import Landing from './landing';
import Login from './login';

const App: React.FC = () => {
    const currentLocation = useLocation();

    useEffect(() => {
        if (currentLocation.pathname === '/') {
            document.body.style.backgroundColor = "#90a68f"
        } else {
            document.body.style.backgroundColor = "#F8F8FD"
        }
    })

    return (
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/landing" element={<Landing username="Flora Demeter" usertype="Administrator" />} />
                <Route path="/:contentType" element={<MainTemplate />} />
            </Routes>
    );
};

export default App;