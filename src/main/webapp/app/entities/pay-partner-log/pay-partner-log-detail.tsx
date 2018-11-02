import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pay-partner-log.reducer';
import { IPayPartnerLog } from 'app/shared/model/pay-partner-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayPartnerLogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PayPartnerLogDetail extends React.Component<IPayPartnerLogDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { payPartnerLogEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PayPartnerLog [<b>{payPartnerLogEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="payLogAmount">Pay Log Amount</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogAmount}</dd>
            <dt>
              <span id="payLogTransRef">Pay Log Trans Ref</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogTransRef}</dd>
            <dt>
              <span id="payLogUsername">Pay Log Username</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUsername}</dd>
            <dt>
              <span id="payLogProductTypeCode">Pay Log Product Type Code</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogProductTypeCode}</dd>
            <dt>
              <span id="payLogResuleCode">Pay Log Resule Code</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogResuleCode}</dd>
            <dt>
              <span id="payLogResuleDesc">Pay Log Resule Desc</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogResuleDesc}</dd>
            <dt>
              <span id="payLogDate">Pay Log Date</span>
            </dt>
            <dd>
              <TextFormat value={payPartnerLogEntity.payLogDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="payLogUDF1">Pay Log UDF 1</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUDF1}</dd>
            <dt>
              <span id="payLogUDF2">Pay Log UDF 2</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUDF2}</dd>
            <dt>
              <span id="payLogUDF3">Pay Log UDF 3</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUDF3}</dd>
            <dt>
              <span id="payLogUDF4">Pay Log UDF 4</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUDF4}</dd>
            <dt>
              <span id="payLogUDF5">Pay Log UDF 5</span>
            </dt>
            <dd>{payPartnerLogEntity.payLogUDF5}</dd>
            <dt>Pay Partner</dt>
            <dd>{payPartnerLogEntity.payPartner ? payPartnerLogEntity.payPartner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/pay-partner-log" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/pay-partner-log/${payPartnerLogEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ payPartnerLog }: IRootState) => ({
  payPartnerLogEntity: payPartnerLog.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PayPartnerLogDetail);
