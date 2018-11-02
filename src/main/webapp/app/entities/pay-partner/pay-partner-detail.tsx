import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pay-partner.reducer';
import { IPayPartner } from 'app/shared/model/pay-partner.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayPartnerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PayPartnerDetail extends React.Component<IPayPartnerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { payPartnerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PayPartner [<b>{payPartnerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="payPartnerCode">Pay Partner Code</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerCode}</dd>
            <dt>
              <span id="payPartnerName">Pay Partner Name</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerName}</dd>
            <dt>
              <span id="payPartnerDesc">Pay Partner Desc</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerDesc}</dd>
            <dt>
              <span id="payPartnerUDF1">Pay Partner UDF 1</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerUDF1}</dd>
            <dt>
              <span id="payPartnerUDF2">Pay Partner UDF 2</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerUDF2}</dd>
            <dt>
              <span id="payPartnerUDF3">Pay Partner UDF 3</span>
            </dt>
            <dd>{payPartnerEntity.payPartnerUDF3}</dd>
          </dl>
          <Button tag={Link} to="/entity/pay-partner" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/pay-partner/${payPartnerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ payPartner }: IRootState) => ({
  payPartnerEntity: payPartner.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PayPartnerDetail);
