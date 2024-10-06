import React from 'react';
import ReactDOM from 'react-dom/client';
import {createBrowserRouter, RouterProvider} from 'react-router-dom'

import App from './App';
import Graficos from './Graficos/Graficos'

import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const router = createBrowserRouter([
  /*{path:"/", element:<App/> },*/
  {path:"/", element:<Graficos/> }
  /*{path:"/welcome", element:<Welcome/> },
  {path:"/user/create", element:<UserCreate/> },
  {path:"/user/update/:id", element:<UserUpdate/>}*/
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);
