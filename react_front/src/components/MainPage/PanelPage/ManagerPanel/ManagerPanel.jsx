import "./ManagerPanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import UsersPage from "../../UsersPage/UsersPage";
import RedactorPage from "../../RedactorPage/RedactorPage";
import ModeratorPage from "../../ModeratorPage/ModeratorPage";
import CountryListPage from "../../CountryListPage/CountryListPage";
import DirectorsListPage from "../../DirectorsListPage/DirectorsListPage";

function ManagerPanel() {

    return(
        <div className={'manager_container'}>
            <div className={'main_menu'}>
                <div className={'menu__info'}>
                    <Avatar/>
                    <span>
                        {localStorage.getItem('fullName')}
                    </span>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'profile'}>
                        Профиль
                    </NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'redactor'}>Редактор карточки фильма</NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'moderation'}>Модерация отзывов</NavLink>
                </div>
                      <div className={'menu__item'}>
                    <NavLink to={'country_list'}>Список стран</NavLink>
                </div>
                      <div className={'menu__item'}>
                    <NavLink to={'directors_list'}>Список режиссёров</NavLink>
                </div>

            </div>
            <div>
                <Routes>
                    <Route path={'profile'} element={<ProfilePage/>}/>
                    <Route path={'redactor'} element={<RedactorPage/>}/>
                    <Route path={'moderation'} element={<ModeratorPage/>}/>
                    <Route path={'country_list'} element={<CountryListPage/>}/>
                    <Route path={'directors_list'} element={<DirectorsListPage/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default ManagerPanel;