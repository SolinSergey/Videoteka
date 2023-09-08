import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import {Component, lazy, Suspense} from "react";
import Footer from "../UI/Footer/Footer";
import {Route, Routes} from "react-router-dom";
// import CatalogPage from "./CatalogPage/CatalogPage";
import CartPage from "./CartPage/CartPage";
import PanelPage from "./PanelPage/PanelPage";
import {CircularProgress} from "@mui/material";
const CatalogPage = lazy(() => import('./CatalogPage/CatalogPage'))



class MainPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            films: [],
            totalPages:null,
            totalElements: null,
            currentPage: 1,
            active: false,
            modal: false
        }
    }




    render() {

        return (
            <div className={style.container}>
                <Header logout={this.props.logout}

                />
                <div className={style.main_container}>
                    <Routes>
                        <Route index path={'/'} element={
                            <Suspense fallback={<CircularProgress color="secondary" sx={{mt: 25, ml: 70}}/>}>
                                <CatalogPage films={this.state.films}
                                                                      totalPages={this.state.totalPages}
                                                                      totalElements={this.state.totalElements}
                                                                      currentPage={this.state.currentPage}
                        />
                            </Suspense>
                            }/>
                        <Route path={'cart'} element={<CartPage/>}/>
                        <Route path={'cabinet/*'} element={<PanelPage/>}/>
                    </Routes>
                </div>
                <div>
                    <Footer/>
                </div>
            </div>
        )
    }
}

export default MainPage;