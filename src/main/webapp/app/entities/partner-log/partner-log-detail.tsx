import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './partner-log.reducer';
import { IPartnerLog } from 'app/shared/model/partner-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPartnerLogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PartnerLogDetail extends React.Component<IPartnerLogDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { partnerLogEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PartnerLog [<b>{partnerLogEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="partnerLogAmount">Partner Log Amount</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogAmount}</dd>
            <dt>
              <span id="partnerLogTransRef">Partner Log Trans Ref</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogTransRef}</dd>
            <dt>
              <span id="partnerLogUsername">Partner Log Username</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUsername}</dd>
            <dt>
              <span id="partnerLogProductTypeCode">Partner Log Product Type Code</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogProductTypeCode}</dd>
            <dt>
              <span id="partnerLogResultCode">Partner Log Result Code</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogResultCode}</dd>
            <dt>
              <span id="partnerLogResultDesc">Partner Log Result Desc</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogResultDesc}</dd>
            <dt>
              <span id="partnerLogDate">Partner Log Date</span>
            </dt>
            <dd>
              <TextFormat value={partnerLogEntity.partnerLogDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="partnerLogUDF1">Partner Log UDF 1</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUDF1}</dd>
            <dt>
              <span id="partnerLogUDF2">Partner Log UDF 2</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUDF2}</dd>
            <dt>
              <span id="partnerLogUDF3">Partner Log UDF 3</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUDF3}</dd>
            <dt>
              <span id="partnerLogUDF4">Partner Log UDF 4</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUDF4}</dd>
            <dt>
              <span id="partnerLogUDF5">Partner Log UDF 5</span>
            </dt>
            <dd>{partnerLogEntity.partnerLogUDF5}</dd>
            <dt>Partner</dt>
            <dd>{partnerLogEntity.partner ? partnerLogEntity.partner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/partner-log" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/partner-log/${partnerLogEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ partnerLog }: IRootState) => ({
  partnerLogEntity: partnerLog.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PartnerLogDetail);
