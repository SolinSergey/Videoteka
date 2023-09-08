import "./PanelPage.css";
import {Component} from "react";
import ProfilePanel from "./ProfilePanel/ProfilePanel";
import AdminPanel from "./AdminPanel/AdminPanel";
import ManagerPanel from "./ManagerPanel/ManagerPanel";

class PanelPage extends Component{
    constructor(props) {
        super(props);
        this.state = {

        }

    }

    getRole = () =>{
        let payload = JSON.parse(localStorage.getItem('customer'))
        let role = payload.role;
        console.log(role)
        switch (role) {
            case 'ROLE_USER':
                return <ProfilePanel/>
            case 'ROLE_ADMIN':
                return <AdminPanel/>
            case 'ROLE_MANAGER':
                return <ManagerPanel/>
            default:
                return <h1>"Нет таких ролей"</h1>

        }
    }
    render() {
        return(
            <div className={'panel_container'}>

                <div className={'panel__box'}>
                    {this.getRole()}
                </div>
            </div>
        )
    }
}

export default PanelPage;