import {GoogleLogin} from "@react-oauth/google";
import {GoogleOAuthProvider} from "@react-oauth/google";

const GoogleLoginButton = () => {
  const clientId = '603277392904-gp1jv0er0ve4l2okpcmk7jvllmh72n5r.apps.googleusercontent.com'

  const handleLoginSuccess = async (response) => {
    console.log(response);

    // Google의 ID 토큰 추출
    const idToken = response.credential;



    try {
      // 백엔드 서버로 ID 토큰 전송
      const res = await fetch('/api/auth/google', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify( idToken)
      });

      if (!res.ok) {
        throw new Error('Failed to authenticate');
      }

      const data = await res.json();
      console.log('User authenticated:', data);
      const accessToken = data.accessToken;
      const refreshToken = data.refreshToken;

      // 쿠키에 accessToken과 refreshToken 저장
      document.cookie = `accessToken=${accessToken}; path=/; max-age=3600`; // 유효기간 1시간
      document.cookie = `refreshToken=${refreshToken}; path=/; max-age=86400`; // 유효기간 1일
      window.location.href = '/';
      console.log(document.cookie);
    } catch (error) {
      console.error('Error during login:', error);
    }
  };

  const handleLoginFailure = (error) => {
    console.log(error);
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