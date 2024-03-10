import React from 'react';
import axios from 'axios';
import EventCreationForm from '../components/EventCreationForm';
import NavbarComponent from '../components/Navbar';

const EventCreatePage = () => {
  const onCreate = async (formData) => {
    try {
      const response = await axios.post('/api/events/register', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log(response.data);
      window.location.href = '/event';
      alert('이벤트가 성공적으로 생성되었습니다.');
    } catch (error) {
      console.error('이벤트 생성 중 에러 발생:', error);
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
