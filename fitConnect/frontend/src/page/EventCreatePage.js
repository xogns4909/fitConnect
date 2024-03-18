import React from 'react';
import axios from 'axios';
import EventCreationForm from '../components/event/EventCreationForm';
import NavbarComponent from '../global/Navbar';
import axiosInstance from "../global/axiosConfig";

const EventCreatePage = () => {
  const onCreate = async (formData) => {
    try {
      const response = await axiosInstance.post('/api/events/register', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      window.location.href = '/event';
      alert('이벤트가 성공적으로 생성되었습니다.');
    } catch (error) {
      alert(error.response.data)
    }
  };

  return (
      <div>
        <NavbarComponent />
        <h1>이벤트 생성</h1>
        <EventCreationForm onCreate={onCreate} />
      </div>
  );
};

export default EventCreatePage;
