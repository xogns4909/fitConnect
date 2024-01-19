import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import MainPage from './page/MainPage';
import LoginPage from './page/LoginPage';

function App() {
  return (
      <GoogleOAuthProvider clientId="603277392904-gp1jv0er0ve4l2okpcmk7jvllmh72n5r.apps.googleusercontent.com">
        <Router>
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<LoginPage />} />
          </Routes>
        </Router>
      </GoogleOAuthProvider>
  );
}

export default App;