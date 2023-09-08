
import './index.css';
import reportWebVitals from './reportWebVitals';
import state from "./adapters/state";
import {rerenderEntireTree} from "./adapters/render";


rerenderEntireTree(state)
reportWebVitals();