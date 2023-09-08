import "./Footer.css";

function Footer(props) {
    return(
        <div className={'footer_container'}>
            <footer>
                <div className="footer-gray">
                    <div className="footer-custom">
                        <div className="footer-lists">
                            <div className="footer-list-wrap">
                                <h6 className="ftr-hdr">Заявочная</h6>
                                <ul className="ftr-links-sub">
                                    <li>8-800-952-5592</li>
                                </ul>
                                <h6 className="ftr-hdr">Международные ресурсы</h6>
                                <ul className="ftr-links-sub">
                                    <li><a href="http://www.example.fr" rel="nofollow">France</a></li>
                                    <li><a href="https://www.example.co.uk" rel="nofollow">United Kingdom</a></li>
                                </ul>
                            </div>
                            {/*/.footer-list-wrap*/}
                            <div className="footer-list-wrap">
                                <h6 className="ftr-hdr">Диспетчерская</h6>
                                <ul className="ftr-links-sub">
                                    <li><a href="/help/talktous.html" rel="nofollow">Контакты</a></li>
                                    <li><a href="/help/placingorders.html" rel="nofollow">Заявки</a></li>
                                    <li><a href="/help/shippingreturns.html" rel="nofollow">Возврат</a></li>
                                    <li><a href="/help/international-shipping.html" rel="nofollow">Международные заказы</a></li>
                                    <li><a href="/~/egift-cards/" rel="nofollow">Подарочные карты</a></li>
                                    <li><a href="/help/faq.html" rel="nofollow">FAQs</a></li>
                                </ul>
                            </div>
                            <div className="footer-list-wrap">
                                <h6 className="ftr-hdr">О example.ru</h6>
                                <ul className="ftr-links-sub">
                                    <li><a href="/asp/aboutus/default-asp/_/posters.htm" rel="nofollow">Компания</a>
                                    </li>
                                    <li><a href="http://corporate.art.com/careers" rel="nofollow">Карьера</a></li>
                                    <li><a href="/asp/landing/artistrising" rel="nofollow">Сотрудники</a></li>
                                    <li><a href="/~/art-for-business" rel="nofollow">Business &amp; Trade Sales</a></li>
                                    <li><a href="http://affiliates.art.com/index.aspx" rel="nofollow">Сотрудничество</a></li>
                                    <li><a href="/catalog" rel="nofollow"><strong>Войти в Каталог</strong></a></li>
                                    <li><a href="http://blog.art.com" rel="nofollow">GB.RU</a></li>
                                </ul>
                            </div>
                            {/*/.footer-list-wrap*/}
                            <div className="footer-list-wrap">
                                <h6 className="ftr-hdr">Учётная Запись</h6>
                                <ul className="ftr-links-sub">
                                    {props.isSale ?
                                        <div>
                                            <li className="ftr-Login"><span
                                                className="link login-trigger">Войти в Учётную Запись</span></li>
                                            <li><span className="link"
                                                      // onClick="link('/asp/secure/your_account/track_orders-asp/_/posters.htm')"
                                            >История Заказов</span>
                                            </li>
                                        </div>
                                        :
                                        <div>
                                        <li className="ftr-Login"><span className="link ftr-access-my-account">Войти в Учётную Запись</span>
                                        </li>
                                        <li><span className="link"
                                        // onClick="window.location.href = getProfileKey() + '?pagetype=oh';"
                                        >История Заказов</span>
                                        </li>
                                        </div>
                                    }
                                </ul>
                            </div>
                            {/*/.footer-list-wrap*/}
                        </div>
                        {/*/.footer-lists*/}
                        <div className="footer-email">
                            <h6 className="ftr-hdr">Подпишись для получения актуальной информации</h6>
                            <div id="ftr-email" className="ftr-email-form">
                                <form id="ftrEmailForm" method="post" action="http://em.example.com/pub/rf">
                                    <div className="error">Введите правильный адрес электронной почты</div>
                                    <input type="text" name="email_address_" id="ftrEmailInput" className="input"
                                           placeholder="Введите адрес эл.почты"/>

                                    <input type="submit" className="button" value="ПОДПИСКА"/>
                                    <input type="hidden" name="country_iso2" value=""/>
                                        <input type="hidden" name="language_iso2" value="en"/>
                                            <input type="hidden" name="site_domain" value="example.com"/>
                                                <input type="hidden" name="email_acq_source" value="01-000001"/>
                                                    <input type="hidden" name="email_acq_date" value=""
                                                           id="ftr-email-date"/>
                                                        <input type="hidden" name="brand_id" value="GB"/>
                                                            <input type="hidden" name="_ri_"
                                                                   value="X0Gzc2X%3DWQpglLjHJlYQGnp51yrMf2qXdC9tjU8pzgMtwfYzaVwjpnpgHlpgneHmgJoXX0Gzc2X%3DWQpglLjHJlYQGnyLSq2fzdkuzdzglHMsUhgeNzaSgkk"/>
                                </form>
                            </div>
                            {/*/.ftr-email-form*/}
                            <div className="ftr-email-privacy-policy"></div>
                        </div>
                        {/*/.footer-email*/}
                        <div className="footer-social">
                            <h6 className="ftr-hdr">Следите за новостями</h6>
                            <ul>
                                <li>
                                    <a href="https://www.facebook.com/example.com" title="Facebook"
                                       // onClick="_gaq.push(['_trackSocial', 'Facebook', 'Follow', 'Footer', 'undefined', 'True']);"
                                    >
                                        <img width="24" height="24" alt="Like us on Facebook"
                                             src="http://cache1.artprintimages.com/images/jump_pages/rebrand/footer/fb.png"/>
                                    </a>
                                </li>
                                <li>
                                    <a href="https://plus.google.com/00000000000000000000" title="Google+"
                                       // onClick="_gaq.push(['_trackSocial', 'GooglePlus', 'Follow', 'Footer', 'undefined', 'True']);"
                                    >
                                        <img width="24" height="24" alt="Follow us on Google+"
                                             src="http://cache1.artprintimages.com/images/jump_pages/rebrand/footer/gplus.png"/>
                                    </a>
                                </li>
                                <li>
                                    <a href="https://pinterest.com/exampledotcom/" target="_blank">
                                        <img width="24" height="24" alt="Follow us on Pinterest"
                                             src="http://cache1.artprintimages.com/images/jump_pages/rebrand/footer/pin-badge.png"/>
                                    </a>
                                </li>
                                <li>
                                    <a target="_blank" href="http://instagram.com/exampledotcom/">
                                        <img width="24" height="24" alt="Follow us on Instagram"
                                             src="http://cache1.artprintimages.com/images/jump_pages/rebrand/footer/instagram-badge.png"/>
                                    </a>
                                </li>
                                <li>
                                    <a href="https://www.twitter.com/exampledotcom" title="Twitter"
                                       // onClick="_gaq.push(['_trackSocial', 'Twitter', 'Follow', 'Footer', 'undefined', 'True']);"
                                    >
                                        <img width="67" alt="Follow us on Twitter"
                                             src="http://cache1.artprintimages.com/images/jump_pages/rebrand/footer/twitter.png"/>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        {/*/.footer-social*/}
                        <div className="footer-legal">
                            <p>&copy; Example.com Inc. All Rights Reserved. | <a href="/help/privacy-policy.html"
                                                                             rel="nofollow">Privacy Policy</a> | <a
                                href="/help/terms-of-use.html" rel="nofollow">Условия Использования</a> | <a
                                href="/help/terms-of-sale.html" rel="nofollow">Условия Распростронения</a></p>
                            <p>Example.com, You+Example, and Photos [to] Example are trademarks or registered trademarks of Example.com
                                Inc.</p>
                        </div>
                        {/*/.footer-legal*/}
                        <div className="footer-payment">
                            <ul>
                                <li className="ftr-stella">
                                    <span title="Stella Service"
                                          // onClick="openLink('http://www.stellaservice.com/profile/Example.com/')"
                                    ></span>
                                </li>
                                <li>
                                    <span
                                        // onClick="clickTrack(); return false;"
                                          // onMouseOver="this.style.cursor='pointer'"
                                    >
                                        <img border="0"
                                                                                         alt="HACKER SAFE certified sites prevent over 99.9% of hacker crime."
                                                                                         src="https://images.scanalert.com/meter/www.art.com/31.gif"/></span>
                                </li>
                                <li className="ftr-bbb">
                                    <span title="BBB"
                                          // onClick="openLink('http://www.bbb.org/raleigh-durham/business-reviews/example-suppliers/artcom-inc-in-raleigh-nc-5001914')"
                                    ></span>
                                </li>
                            </ul>
                        </div>
                        {/*/.footer-payment*/}
                    </div>
                    {/*/.footer-custom*/}
                </div>
                {/*/.footer-gray*/}
            </footer>
        </div>
    )
}

export default Footer;
