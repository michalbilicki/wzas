import * as React from "react";
import BottomNavigation from "@material-ui/core/BottomNavigation";
import BottomNavigationAction from "@material-ui/core/BottomNavigationAction";
import makeStyles from "@material-ui/core/styles/makeStyles";
import RestoreIcon from '@material-ui/icons/Restore';
import FavoriteIcon from '@material-ui/icons/Favorite';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import Grid from "@material-ui/core/Grid";
import '../resources/css/main.css'

class MainPageComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: ""
        }

    }

    useStyles = makeStyles({
        root: {
            width: 500,
        },
    });

    handleChange = (newValue) => {
        this.setState({
            value: newValue
        });
    };

    render() {
        return (<p>TEST</p>
                // <Grid container>
                //     <Grid style={{maxHeight: 30}} item xs={12}>
                //         <div>
                //             MENU
                //         </div>
                //     </Grid>
                //     <Grid item xs={12}>
                //         CONTETN
                //     </Grid>
                // </Grid>
        );
    }
}

export default MainPageComponent;

