import {NavLink} from "react-router-dom";
import {Avatar} from "@mui/material";
import {useEffect, useRef, useState} from "react";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart'
import "./Header.css"
import DropdownItem from "../../../widgets/DropdownItem/DropdownItem";


function Header(props) {

    let username = JSON.parse(localStorage.getItem('username'))
    let fullName = localStorage.getItem('fullName')
    console.log(fullName)
    let role = JSON.parse(localStorage.getItem('role_user'))
    console.log('Присвоена роль: ' + role)

    let getCurrentUser = () => {
        return JSON.parse(localStorage.getItem('customer'))
    }

    let user = getCurrentUser()


    let logout = () => {
        localStorage.removeItem("customer")
        localStorage.removeItem("username")
        localStorage.removeItem("fullName")
        user = null
        window.location = '/'
    }
    let loginRedirector = () => {
        window.location = "/gate/login"
    }
    let openMenu = () => {
        setOpen(!open)
    }


    let roleMenu = () => {
        switch (role) {
            case 'ROLE_USER':
                return <div>
                    <NavLink to={'cart'}>
                        <DropdownItem text={'корзина'}/>
                    </NavLink>
                    <NavLink to={'cabinet/orders'}>
                        <DropdownItem text={'мои фильмы'}/>
                    </NavLink>
                </div>
            case 'ROLE_ADMIN':
                return <NavLink to={'cabinet/users'}>
                    <DropdownItem text={'пользователи'}/>
                </NavLink>
            case 'ROLE_MANAGER':
                return (
                    <div>
                        <NavLink to={'cabinet/redactor'}>
                            <DropdownItem text={'редактор фильмов'}/>
                        </NavLink>
                        <NavLink to={'cabinet/moderation'}>
                            <DropdownItem text={'модерация отзывов'}/>
                        </NavLink>
                        <NavLink to={'cabinet/country_list'}>
                            <DropdownItem text={'список стран'}/>
                        </NavLink>
                        <NavLink to={'cabinet/directors_list'}>
                        <DropdownItem text={'список режиссёров'}/>
                    </NavLink>
                    </div>
                )
            default:
                return
        }

    }

    const [open, setOpen] = useState(false);

    let menuRef = useRef();
    useEffect(() => {
        if (user) {
            let handler = (event) => {
                if (!menuRef.current.contains(event.target)) {
                    setOpen(false);
                }
            }
            document.addEventListener("mousedown", handler);
            return () => {
                document.removeEventListener("mousedown", handler);
            }
        }
    });

    return (
        <div className={'header'}>
            <div className={'top_header'}>
                <NavLink to={'/'} className={'logo'}>
                    <img
                        src={'https://i.pinimg.com/originals/6a/e2/02/6ae2025b41de91553621b2c8c554d61f.jpg'}
                        alt={'logo'}/>
                </NavLink>
                <div className={'menu_deck'}>
                    {user ?
                        <div className={'auth_container'}>
                            <div className={'menu_container'} ref={menuRef}>
                                <div className={'dropdown_trigger'} onClick={openMenu}>
                                    <Avatar
                                        className={'iconblock__avatar'}
                                        src={'/'}
                                        sx={{
                                            width: 36,
                                            height: 36,
                                        }}
                                    />
                                    <div className={'user_name__under_avatar'}>{fullName}</div>
                                </div>
                                <div className={`dropdown_menu ${open ? 'active' : 'inactive'}`}>
                                    <h3 className={'menu_username'}>
                                        {fullName}
                                        <span className={'menu_location'}></span></h3>
                                    <ul>
                                        <NavLink to={'cabinet/profile'}>
                                            <DropdownItem text={'профиль'}/>
                                        </NavLink>
                                        {roleMenu()}
                                        <button className={'logout_btn'} onClick={() => logout()}>
                                            <DropdownItem text={'выход'}/>
                                        </button>

                                    </ul>
                                </div>

                            </div>
                            <div className={'cart_box'}>
                                {role === 'ROLE_USER'?
                                    <NavLink to={'/cart'} className={'cart_box__button'}>
                                    <ShoppingCartIcon/>
                                    </NavLink>
                                    :
                                    null
                                }

                            </div>
                        </div>
                        :
                        <div className={'login_btn'}>
                            <button onClick={() => loginRedirector()}>Войти</button>
                        </div>
                    }


                </div>

            </div>

        </div>
    )
}

export default Header;