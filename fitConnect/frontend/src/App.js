import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import MainPage from './page/MainPage';
import LoginPage from './page/LoginPage';
import PostListPage from './page/PostListPage';
import {AuthProvider} from "./global/AuthContext";
import Navbar from "./components/Navbar";

function App() {
  return (
      <AuthProvider>
      <GoogleOAuthProvider clientId="603277392904-gp1jv0er0ve4l2okpcmk7jvllmh72n5r.apps.googleusercontent.com">
        <Router>
          <Routes>

            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/event" element={<PostListPage />} />
          </Routes>
        </Router>
      </GoogleOAuthProvider>
      </AuthProvider>
  );
}

export default App;
