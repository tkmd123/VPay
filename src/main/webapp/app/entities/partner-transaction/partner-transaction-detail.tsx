import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './partner-transaction.reducer';
import { IPartnerTransaction } from 'app/shared/model/partner-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPartnerTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PartnerTransactionDetail extends React.Component<IPartnerTransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { partnerTransactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PartnerTransaction [<b>{partnerTransactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="partnerTransAmount">Partner Trans Amount</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransAmount}</dd>
            <dt>
              <span id="partnerTransDate">Partner Trans Date</span>
            </dt>
            <dd>
              <TextFormat value={partnerTransactionEntity.partnerTransDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="partnerTransRef">Partner Trans Ref</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransRef}</dd>
            <dt>
              <span id="partnerTransUsername">Partner Trans Username</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransUsername}</dd>
            <dt>
              <span id="partnerTransUDF1">Partner Trans UDF 1</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransUDF1}</dd>
            <dt>
              <span id="partnerTransUDF2">Partner Trans UDF 2</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransUDF2}</dd>
            <dt>
              <span id="partnerTransUDF3">Partner Trans UDF 3</span>
            </dt>
            <dd>{partnerTransactionEntity.partnerTransUDF3}</dd>
            <dt>Product Type</dt>
            <dd>{partnerTransactionEntity.productType ? partnerTransactionEntity.productType.id : ''}</dd>
            <dt>Partner</dt>
            <dd>{partnerTransactionEntity.partner ? partnerTransactionEntity.partner.id : ''}</dd>
            <dt>Wallet Transaction</dt>
            <dd>{partnerTransactionEntity.walletTransaction ? partnerTransactionEntity.walletTransaction.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/partner-transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/partner-transaction/${partnerTransactionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ partnerTransaction }: IRootState) => ({
  partnerTransactionEntity: partnerTransaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PartnerTransactionDetail);
