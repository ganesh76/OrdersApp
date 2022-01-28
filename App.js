/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

 import React, { Component } from 'react';
 import {
   Platform,
   StyleSheet,
   Text,
   View,Button,Alert
 } from 'react-native';
 import OrdersModule from './OrdersModule';
 
 export default class App extends Component<Props> {
 constructor(props) {
     super(props);
     this.state = {orderobj: "empty",completed:false};
   }
   componentWillMount() 
 {
         OrdersModule.getOrderData((obj)=>
       {
          console.log("id is == "+JSON.parse(obj).id);
          console.log("completed is == "+JSON.parse(obj).completed);
          this.setState({orderobj: JSON.parse(obj)});
          if(JSON.parse(obj).completed === 'true')
          {
             this.setState({completed:true});
             console.log("completed if"+JSON.parse(obj).completed);
          }
          else
          {
             this.setState({completed:false});
             console.log("completed else"+JSON.parse(obj).completed);
          }
       });
 }
 
 
   render() {
 
      if(this.state.completed == true)
          {
              return (
                   <View style={styles.container}>
 
                     <View style={styles.boxView}>
 
                       <Text style={styles.title}>
                       Order Details Page
                        </Text>
 
                       <Text style={styles.title}>
                       Title : {this.state.orderobj.title}
                       </Text>
 
                       <Text style={styles.title}>
                       Order Status : Order Placed
                       </Text>
 
                     </View>
 
                   </View>
                 );
          }
          else
          {
              return (
                   <View style={styles.container}>
 
                     <View style={styles.boxView}>
 
                     <Text style={styles.title}>
                       Order Details Page
                        </Text>
 
                       <Text style={styles.title}>
                       Title : {this.state.orderobj.title}
                       </Text>
 
                       <Text style={styles.title}>
                        Order Status : Order Pending
                       </Text>
 
                       <Text style={styles.title}>
                       Press the below Button to change Order Status
                       </Text>
 
                      <Button
                       onPress={() => {
                        OrdersModule.changeOrderStatus(this.state.orderobj.id);
                        this.setState({completed:true});
                        Alert.alert('Order Placed Successfully');
                       }}
                       title="Place Order"
                     />
 
                     </View>
 
                   </View>
                 );
          }
    
   }
 }
 
 const styles = StyleSheet.create({
   container: {
     flex: 1,
     justifyContent: 'center',
     alignItems: 'center',
     backgroundColor: '#F0F8FF',
   },
   boxView: {
     justifyContent: 'center',
     alignItems: 'center',
     backgroundColor: '#fff',
     borderWidth:2,
     borderColor:'#000',
     borderRadius:2,
     padding:10,
     margin:10,
   },
   title: {
      fontSize: 14,
     fontWeight: 'bold',
     textAlign: 'center',
     color: '#333333',
     padding: 5,
   }
 });
 