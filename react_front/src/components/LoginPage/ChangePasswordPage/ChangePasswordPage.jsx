import "./ChangePasswordPage.css"
import {Box, useMediaQuery} from "@mui/material";
import {useState} from "react";
import {determineModalContent} from "../../../utils/RegisterModalUtils.tsx";
import {RegistrationStepCounter} from "../../../widgets/RegistrationStepCounter/RegistrationStepCounter.tsx";


const ChangePasswordPage = (props) => {
  const isNonMobileScreens = useMediaQuery("(min-width: 1000px)")
  const [step, setStep] = useState(1)
  const stepButtonClickHandler = () => {
    step>1 ? setStep(step-1) : setStep(1)
  }

  return(
      <Box
          width={isNonMobileScreens ? "30rem" : "93%"}
          height={isNonMobileScreens ? "40rem" : "93%"}
          p='1rem'
          borderRadius='0.3rem'
          backgroundColor={"white"}
      >

          <Box display='flex' justifyContent='flex-start'>
            <RegistrationStepCounter step={step} changeStep={() => stepButtonClickHandler()}/>
          </Box>
          <Box
              display='flex'
              justifyContent='center'
              p='1rem'
              alignItems={'center'}
              textAlign={'center'}
          >
              {determineModalContent(step)}
          </Box>
        {/*<Form />*/}
      </Box>
  )
}
export default ChangePasswordPage