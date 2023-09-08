import "./ListCard.css"

const ListCard = (props) => {
    // let UncheckHandler = () => {
    //     if (!props.checked){
    //         props.setChecked(true)
    //     }
    //     if (props.checked){
    //         props.setChecked(false)
    //     }
    //
    // }
  return(
      <div className={'list_card__container'}>
          <div className={'list_card__content'}>
              <span className={'list_card__text'}>{props.msg}</span>
              <input type={'checkbox'} value={props.msg} onChange={props.changeStateHandler.bind(this)}/>
          </div>
      </div>
  )
}
export default ListCard