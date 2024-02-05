import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // 여기서는 예시로 localStorage를 사용합니다. 실제로는 서버로부터의 인증 확인이 필요합니다.
    const status = localStorage.getItem('isLoggedIn') === 'true';
    setIsLoggedIn(status);
  }, []);

  const login = () => {
    setIsLoggedIn(true);
    window.location.href = '/event';
    console.log("isLoggedIn true")
    localStorage.setItem('isLoggedIn', 'true');
  };


  const logout = async () => {
    try {
      // 서버에 로그아웃 요청
      const response = await fetch('/api/auth/logout', {
        method: 'POST',
        credentials: 'include',
      });

      if (!response.ok) throw new Error('Logout failed');


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