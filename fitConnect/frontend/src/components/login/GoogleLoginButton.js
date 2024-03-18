import {GoogleLogin} from "@react-oauth/google";
import {GoogleOAuthProvider} from "@react-oauth/google";
import {useAuth} from "../../global/AuthContext";
import React, { useState, useEffect } from 'react';

const GoogleLoginButton = ({ onLoginSuccess }) => {
  const { login } = useAuth();
  const clientId = '603277392904-gp1jv0er0ve4l2okpcmk7jvllmh72n5r.apps.googleusercontent.com'




  const handleLoginSuccess = async (response) => {

    // Google의 ID 토큰 추출
    const idToken = response.credential;



    try {
      const res = await fetch('/api/auth/google', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify( idToken)
      });

      if (!res.ok) {
        throw new Error('Failed to authenticate');
      }


      login();

    } catch (error) {
      console.error('Error during login:', error);
    }
  };

  const handleLoginFailure = (error) => {
  };

  return (
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
            onSuccess={handleLoginSuccess}
            onFailure={handleLoginFailure}
        />
      </GoogleOAuthProvider>
  );
};

export default GoogleLoginButton;