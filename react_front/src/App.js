import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./components/MainPage/MainPage";
import LoginPage from "./components/LoginPage/LoginPage";


function App(props) {
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route path={'/*'} element={

                    <MainPage
                    logout={props.logout}
                    getCurrentUser={props.getCurrentUser}
                    addFilm={props.addFilm}
                    getAllFilms={props.getAllFilms}
                    getMinMaxPrice={props.getMinMaxPrice}
                    getAllGenres={props.getAllGenres}
                    getAllDirectors={props.getAllDirectors}
                    getAllCountries={props.getAllCountries}
                    clearState={props.clearState}
                />

               }/>
              <Route path='/gate/*' element={<LoginPage/>}/>
          </Routes>
        </BrowserRouter>
      </div>
  );
}


export default App;
