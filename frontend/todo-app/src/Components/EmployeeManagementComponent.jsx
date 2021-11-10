
import Container from 'react-bootstrap/Container';
import Placeholder from 'react-bootstrap/Placeholder';
import React, { Component } from 'react';
import UserDataService from '../api/UserDataService';
import { SESSION_USER_BUSINESS } from '../Constants';



class EmployeeManagementComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            employees: [],
        }
    }

    componentDidMount() {
        UserDataService.retrieveUsersByBusiness(sessionStorage.getItem(SESSION_USER_BUSINESS))
            .then(response => {
                this.setState({ employees: response.data })
            });
    }

    render() {
        return (
            <div style={{
                backgroundImage: "url(https://image.freepik.com/free-vector/white-faded-gradient-background-vector-with-orange-border_53876-125752.jpg"
                , backgroundPosition: 'center'
                , backgroundSize: 'cover'
                , backgroundRepeat: 'no-repeat'
                , width: '100%'
                , height: '66rem'
            }}>
                <Container>
                    <Placeholder xs={12} bg="transparent" />
                    <h1 className="text-dark" >Your Employees</h1>
                    <Placeholder xs={12} bg="transparent" />
                    <h3 class="text-dark ">Employees and COVID/SHN Status</h3>
                </Container>
                <Placeholder xs={12} bg="transparent" />
                <Placeholder xs={12} bg="transparent" />
                <Container>
                    <table className="table text-dark">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Has Covid</th>
                                <th>Has SHN</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.employees.map(
                                    employee =>
                                        <tr key={employee.id}>
                                            <td>{employee.name}</td>
                                            <td>{employee.email}</td>
                                            <td>{employee.covidStatus.toString()}</td>
                                            <td>{employee.shnStatus.toString()}</td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </Container>
            </div>
        )
    }


}

export default EmployeeManagementComponent;