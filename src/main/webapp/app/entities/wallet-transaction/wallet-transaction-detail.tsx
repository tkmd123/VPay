import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './wallet-transaction.reducer';
import { IWalletTransaction } from 'app/shared/model/wallet-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWalletTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WalletTransactionDetail extends React.Component<IWalletTransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { walletTransactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            WalletTransaction [<b>{walletTransactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Wallet Transction Type</dt>
            <dd>{walletTransactionEntity.walletTransctionType ? walletTransactionEntity.walletTransctionType.id : ''}</dd>
            <dt>Wallet Allotment</dt>
            <dd>{walletTransactionEntity.walletAllotment ? walletTransactionEntity.walletAllotment.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/wallet-transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/wallet-transaction/${walletTransactionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ walletTransaction }: IRootState) => ({
  walletTransactionEntity: walletTransaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WalletTransactionDetail);
