import  {FC} from 'react'
import {Box, Typography, useMediaQuery, useTheme} from "@mui/material"
import FlexBetween from "../../components/UI/FlexBetween"
import "./RegistrationStepCounter.css"
// @ts-ignore
import {displayIcon, iconClass} from "../../utils/RegisterStepUtils.tsx"
interface RegistrationStepProps {
    step: number

    changeStep(): void
}

export const RegistrationStepCounter: FC<RegistrationStepProps> = ({step, changeStep}) => {
    const theme = useTheme()
    const neutralLight = theme.palette.primary.main
    return (

        <FlexBetween>
            <Box display='flex'
                justifyContent='center'
                p='1rem'
                width={'64px'}
                className={iconClass(step)}
                sx={{
                    "&:hover": {
                        color: neutralLight,
                        cursor: "pointer",
                    },
                }}>
                <div onClick={changeStep}>
                    {displayIcon(step)}
                </div>

            </Box>
            <Box display={'flex'} alignItems={'center'} textAlign={'center'}>
                <Typography fontWeight='500' variant='h5'>
                    Шаг {step} из 6
                </Typography>
            </Box>
        </FlexBetween>
    )
}

