import React from 'react';
import axiosInstance from '../global/axiosConfig';
import EventCreationForm from '../components/EventCreationForm';
import NavbarComponent from '../components/Navbar';
const EventCreatePage = () => {


  const onCreate = async (eventData) => {
    try {
      const response = await axiosInstance.post('/api/events/register', eventData, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      alert('이벤트가 성공적으로 생성되었습니다.');
      window.location.href = '/event'
    } catch (error) {
      console.error('이벤트 생성 중 에러 발생:', error);

      alert(error.response.data.message || '이벤트 생성에 실패했습니다.');
    }
  };

  return (
      <div>
        <NavbarComponent/>
        <h1>이벤트 생성</h1>
        <EventCreationForm onCreate={onCreate} />
      </div>
  );
};

export default EventCreatePage;
