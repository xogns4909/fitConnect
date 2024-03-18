import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import RequireAuth from './global/RequireAuth';
import { AuthProvider } from './global/AuthContext';

import MainPage from './page/MainPage';
import LoginPage from './page/LoginPage';
import PostListPage from './page/PostListPage';
import EventCreatePage from './page/EventCreatePage';
import EventDetailPage from './page/EventDetailPage';
import ChatRoomListPage from './page/ChatRoomListPage';
import MyPage from './page/MyPage';
import EventEditForm from './components/event/EventEdit';
import NotFoundPage from './page/NotFoundPage';
import UserInfo from './components/mypage/UserInfo';
import ReviewsList from './components/mypage/ReviewList';
import RegisteredEventsList from './components/mypage/RegisteredEventsList';
import MyEventsList from './components/mypage/MyEventsList';

function App() {

  return (
        <GoogleOAuthProvider clientId="YOUR_CLIENT_ID">
          <Router>
            <AuthProvider>
            <Routes>
              <Route path="/" element={<MainPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/event" element={<PostListPage />} />
              <Route path="/new-post" element={
                <RequireAuth>
                  <EventCreatePage />
                </RequireAuth>
              } />
              <Route path="/events/:eventId" element={
                <RequireAuth>
                  <EventDetailPage />
                </RequireAuth>
              } />
              <Route path="/chatRooms" element={
                <RequireAuth>
                  <ChatRoomListPage />
                </RequireAuth>
              } />
              <Route path="/myPage" element={
                <RequireAuth>
                  <MyPage />
                </RequireAuth>
              }>
                <Route path="user-info" element={<UserInfo />} />
                <Route path="review" element={<ReviewsList />} />
                <Route path="registered-events" element={<RegisteredEventsList />} />
                <Route path="my-events" element={<MyEventsList />} />
              </Route>
              <Route path="/events/edit/:eventId" element={
                <RequireAuth>
                  <EventEditForm />
                </RequireAuth>
              } />
              <Route path="*" element={<NotFoundPage />} />
            </Routes>
            </AuthProvider>
          </Router>
        </GoogleOAuthProvider>
  );
}

export default App;
