import "./CartPage.css"
import {NavLink} from "react-router-dom";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import StringCard from "../../../widgets/StringCard/StringCard";
import React, {Component} from "react";
import axios from "axios";
import CheckoutCard from "../../../widgets/CheckoutCard/CheckoutCard";
import {ArrowForward, ArrowForwardIos} from "@mui/icons-material";

class CartPage extends Component{
    constructor(props) {
        super(props);
        this.state = {
            bayPack: [],
            totalPrice:'',

        }
    }
    componentDidMount() {
        this.loadCart()
    }

    loadCart = () =>{
        console.log(localStorage.getItem("guestCartId"))
        axios.get("http://localhost:5555/cart/api/v1/cart/",{
            params: {
                uuid: localStorage.getItem("guestCartId")
            }
        })
            .then(response => response.data)
            .then(data  => {this.setState({

                bayPack: data.items,
                totalPrice: data.totalPrice
            })
                console.log(data)
                localStorage.setItem("totalPrice", JSON.stringify(data.totalPrice))

            })

    }
   clearCart = async () => {
       try {
           const response = await axios.get('http://localhost:5555/cart/api/v1/cart/clear',
               {
                   params: {
                       uuid: localStorage.getItem('guestCartId'),
                   }
               }
           )
           console.log("Ответ метода clearCart: " + response)
          this.loadCart()
       } catch (e) {
           alert("Метод clearCart: " + e)
       }
   }
    render() {


        return (
            <div className={'cart_container'}>
                <div className={'cart_container__plate'}>

                    <div className={'cart_container__header'}>
                        <NavLink to={'/'}>
                            <span className={'to_catalog'}><ArrowBackIcon/><h4>Выбрать новые фильмы</h4></span>
                        </NavLink>
                        <div className={'to_cabinet__btn'}>
                            <NavLink to={'/cabinet/orders'}>
                                <span className={'to_orders'}><h4>Мои фильмы</h4><ArrowForward/></span>
                            </NavLink>
                        </div>


                        <div className={'delimiter'}>
                            <span>.</span>
                        </div>
                        <div className={'details'}>
                            {this.state.bayPack.length > 0 ?
                                <span>У вас в корзине {this.state.bayPack.length} ед. товара</span>
                                :
                                <span>У вас в корзине нет товаров</span>
                            }

                        </div>
                    </div>

                    <div className={'card_box'}>
                        {this.state.bayPack.map((item) =>
                                <StringCard filmId={item.filmId}
                                            title={item.title}
                                            isSale={item.sale}
                                            salePrice={item.salePrice}
                                            rentPrice={item.rentPrice}
                                            cover={item.imageUrlLink}
                                            loadCart={this.loadCart}

                                />
                            )
                        }

                    </div>
                    <div>
                        <button className={'clear_button'} onClick={() => this.clearCart()}>Очистить корзину</button>
                    </div>
                </div>
                <div className={'checkout'}>
                    <CheckoutCard clearCart={this.clearCart}
                                  totalPrice={this.state.totalPrice}

                    />
                </div>
            </div>
        )
    }
}

export default CartPage;