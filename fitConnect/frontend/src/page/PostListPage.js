import React, { useState, useEffect } from 'react';
import Navbar from '../global/Navbar';
import SearchComponent from '../global/SearchComponents';
import EventListComponent from '../components/event/EventListComponent';
import { Pagination } from 'react-bootstrap';
import axiosInstance from "../global/axiosConfig"; // axiosInstance 임포트
import { useNavigate } from 'react-router-dom';

const PostListPage = () => {
  const navigate = useNavigate();
  const [events, setEvents] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [category, setCategory] = useState('');
  const [city, setCity] = useState('');
  const [description, setDescription] = useState('');
  const [searchBy, setSearchBy] = useState('title');

  useEffect(() => {
    fetchEvents();
  }, [page]);

  const fetchEvents = async () => {
    try {
      const url = `/api/events?page=${page}&size=${size}&category=${category}&city=${city}&description=${description}&searchBy=${searchBy}`;
      const response = await axiosInstance.get(url);
      setEvents(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching events:', error);
    }
  };

  const handleLogout = async () => {
    try {
      const response = await axiosInstance.post('/api/auth/logout'); // axiosInstance 사용
      navigate('/');
    } catch (error) {
      console.error('Error during logout:', error);
    }
  };

  const handleSearch = () => {
    setPage(0);
    fetchEvents();
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  const paginationItems = [];
  for (let number = 1; number <= totalPages; number++) {
    paginationItems.push(
        <Pagination.Item
            key={number}
            active={number === page + 1}
            onClick={() => handlePageChange(number - 1)}
        >
          {number}
        </Pagination.Item>
    );
  }

  return (
      <div>
        <Navbar onLogout={handleLogout} />
        <div className="container mt-5">
          <h1>이벤트 목록</h1>
          <SearchComponent
              setCategory={setCategory}
              setCity={setCity}
              setDescription={setDescription}
              setSearchBy={setSearchBy}
              onSearch={handleSearch}
          />

          <br />
          <EventListComponent events={events} />

          <div className="d-flex justify-content-center">
            <Pagination>{paginationItems}</Pagination>
          </div>
        </div>
      </div>
  );
};

export default PostListPage;
