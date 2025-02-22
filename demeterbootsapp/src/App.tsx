import React from 'react';
import { useEffect}  from 'react';
import { Routes, Route, useLocation} from 'react-router-dom';
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
    }, [currentLocation]);

    return (
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/landing" element={<Landing username="Flora Demeter" usertype="Administrator" />} />
                <Route path="/:contentType" element={<MainTemplate />} />
            </Routes>
    );
};

export default App;