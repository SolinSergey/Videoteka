import "./ProfilePanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import {useState} from "react";

function ProfilePanel() {

    // const [link, setLink] = useState(null)

    return (
        <div className={'profile_container'}>
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
                    <NavLink to={'/cart'}>
                        Корзина
                    </NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'orders'}>Мои фильмы</NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'favourites'}>Избранное</NavLink>
                </div>
            </div>
            <div>
                <Routes>
                    <Route path={'profile'} element={<ProfilePage/>}/>
                    <Route path={'orders'} element={<OrdersPage/>}/>
                    <Route path={'favourites'} element={<FavoritesPage/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default ProfilePanel;