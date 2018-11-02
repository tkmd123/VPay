import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './pay-partner-log.reducer';
import { IPayPartnerLog } from 'app/shared/model/pay-partner-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayPartnerLogProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IPayPartnerLogState {
  search: string;
}

export class PayPartnerLog extends React.Component<IPayPartnerLogProps, IPayPartnerLogState> {
  state: IPayPartnerLogState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { payPartnerLogList, match } = this.props;
    return (
      <div>
        <h2 id="pay-partner-log-heading">
          Pay Partner Logs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Pay Partner Log
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput type="text" name="search" value={this.state.search} onChange={this.handleSearch} placeholder="Search" />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Pay Log Amount</th>
                <th>Pay Log Trans Ref</th>
                <th>Pay Log Username</th>
                <th>Pay Log Product Type Code</th>
                <th>Pay Log Resule Code</th>
                <th>Pay Log Resule Desc</th>
                <th>Pay Log Date</th>
                <th>Pay Log UDF 1</th>
                <th>Pay Log UDF 2</th>
                <th>Pay Log UDF 3</th>
                <th>Pay Log UDF 4</th>
                <th>Pay Log UDF 5</th>
                <th>Pay Partner</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {payPartnerLogList.map((payPartnerLog, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${payPartnerLog.id}`} color="link" size="sm">
                      {payPartnerLog.id}
                    </Button>
                  </td>
                  <td>{payPartnerLog.payLogAmount}</td>
                  <td>{payPartnerLog.payLogTransRef}</td>
                  <td>{payPartnerLog.payLogUsername}</td>
                  <td>{payPartnerLog.payLogProductTypeCode}</td>
                  <td>{payPartnerLog.payLogResuleCode}</td>
                  <td>{payPartnerLog.payLogResuleDesc}</td>
                  <td>
                    <TextFormat type="date" value={payPartnerLog.payLogDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{payPartnerLog.payLogUDF1}</td>
                  <td>{payPartnerLog.payLogUDF2}</td>
                  <td>{payPartnerLog.payLogUDF3}</td>
                  <td>{payPartnerLog.payLogUDF4}</td>
                  <td>{payPartnerLog.payLogUDF5}</td>
                  <td>
                    {payPartnerLog.payPartner ? (
                      <Link to={`pay-partner/${payPartnerLog.payPartner.id}`}>{payPartnerLog.payPartner.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payPartnerLog.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payPartnerLog.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payPartnerLog.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ payPartnerLog }: IRootState) => ({
  payPartnerLogList: payPartnerLog.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PayPartnerLog);
