import './AddCountryPage.css'
import style from "../RedactorPage/RedactorPage.module.css";
import React, {useState} from "react";
import axios from "axios";
import {FormControl} from "@mui/material";
import {clear} from "@testing-library/user-event/dist/clear";

const AddCountryPage = (props) => {
    let addNewCountry = () => {
        axios.post('http://localhost:5555/catalog/api/v1/country/new', {
            title: country
        }).then(r => r)
            .then(() => {
                props.getCountries()
                setCountry('')
                props.setActive(false)
            }, () => setCountry(''))
    }
    let onChangeHandler = (event) => {
        setCountry(event.target.value)
    }
    const [country, setCountry] = useState("")
  return(
      <div className={'add_country__container'}>
          <div className={'add_country__title'}>Добавить страну</div>
          <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
          <div className={'input_container'}>
              <input type="text" required="" placeholder={'Название страны'} value={country || ''} onChange={(event) => onChangeHandler(event)} onfocusout={clear}/>
          </div>
          <div className={'add_country__send_btn'}>
              <button onClick={() => addNewCountry()}>Сохранить страну</button>
          </div>
          </FormControl>
      </div>
  )
}

export default AddCountryPage