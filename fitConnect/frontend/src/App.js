import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import MainPage from './page/MainPage';
import LoginPage from './page/LoginPage';
import PostListPage from './page/PostListPage';
import {AuthProvider} from "./global/AuthContext";
import EventCreatePage from "./page/EventCreatePage";
import EventDetailPage from "./page/EventDetailPage";
import CreateChatRoom from "./components/CreateChatRoom";
import ChatRoomListPage from "./page/ChatRoomListPage";
import MyPage from "./page/MyPage";
import EventEditForm from "./components/EventEdit";
import NotFoundPage from "./page/NotFoundPage";
function App() {
  return (
      <AuthProvider>
      <GoogleOAuthProvider clientId="603277392904-gp1jv0er0ve4l2okpcmk7jvllmh72n5r.apps.googleusercontent.com">
        <Router>
          <Routes>
            <Route path="/new-post" element={<EventCreatePage/>} />
            <Route path="/" element={<MainPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/event" element={<PostListPage />} />
            <Route path="/events/:eventId" element={<EventDetailPage />} />
            <Route path="/chatRooms" element={<ChatRoomListPage/>} />
            <Route path="/myPage" element={<MyPage/>} />
            <Route path="/events/edit/:eventId" element={<EventEditForm />} />
            <Route path="*" element={<NotFoundPage />} /> {}
          </Routes>
        </Router>
      </GoogleOAuthProvider>
      </AuthProvider>
  );
}

export default App;
