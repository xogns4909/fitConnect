import React, { createContext, useContext, useState, useEffect } from 'react';
import axiosInstance from '../global/axiosConfig'
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem('isLoggedIn') === 'true');
  const navigate = useNavigate();

  useEffect(() => {
  }, []);

  const login = () => {
    localStorage.setItem('isLoggedIn', 'true');
    setIsLoggedIn(true);
    navigate('/event');
  };

  const logout = async () => {
    try {
      const response = await axiosInstance.post('/api/auth/logout')
      localStorage.removeItem('isLoggedIn');
      setIsLoggedIn(false);

      window.location.href = '/';
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  return (
      <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
        {children}
      </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
