import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import SearchComponent from '../components/SearchComponents';
import EventListComponent from '../components/EventListComponent';
import { Pagination } from 'react-bootstrap';

const PostListPage = () => {
  const [events, setEvents] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [category, setCategory] = useState('');
  const [city, setCity] = useState('');
  const [description, setDescription] = useState('');


  useEffect(() => {
    fetchEvents();
  }, [page]);

  const fetchEvents = async () => {
    try {
      const url = `/api/events?page=${page}&category=${category}&city=${city}&description=${description}`;
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setEvents(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error('Error fetching events:', error);
    }
  };

  const handleLogout = async () => {
    try {
      const response = await fetch('/api/auth/logout', { method: 'POST' });
      if (!response.ok) {
        throw new Error('Logout failed');
      }

      window.location.href = '/';
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
              onSearch={handleSearch}
          />
          <br/>
          <EventListComponent events={events} />

          <div className="d-flex justify-content-center">
            <Pagination>{paginationItems}</Pagination>
          </div>
        </div>
      </div>
  );
};


export default PostListPage;
