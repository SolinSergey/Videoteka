import "./AdminPanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import UsersPage from "../../UsersPage/UsersPage";

function AdminPanel() {
    return(
        <div className={'admin_container'}>
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
                    <NavLink to={'users'}>Пользователи</NavLink>
                </div>
            </div>
            <div>
                <div>
                    <Routes>
                        <Route path={'profile'} element={<ProfilePage/>}/>
                        <Route path={'orders'} element={<OrdersPage/>}/>
                        <Route path={'favourites'} element={<FavoritesPage/>}/>
                        <Route path={'users'} element={<UsersPage/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default AdminPanel;