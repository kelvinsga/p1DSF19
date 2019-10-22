package edu.uprm.cse.datastructures.cardealer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarList;
import edu.uprm.cse.datastructures.cardealer.util.CircularSortedDoublyLinkedList;



@Path("/cars")
public class CarManager {

private final CircularSortedDoublyLinkedList<Car> cList = CarList.getInstance();

//Read all cars
@GET
@Produces(MediaType.APPLICATION_JSON)
public Car[] getCarList() {
return cList.toArray();
}

//Get the id
@GET
@Path("{id}")
@Produces(MediaType.APPLICATION_JSON)
public Car getCar(@PathParam("id") long id){
for(Car currentCar : cList){
if(currentCar.getCarId()==id){
	return currentCar;
	}  
}  
throw new NotFoundException(new JsonError("Error", "Car " + id + " not found"));
}

//Add a car to carDealer
@POST
@Path("/add")
@Produces(MediaType.APPLICATION_JSON)
public Response addCar(Car car){
for(Car currentCar : cList){
if(currentCar.getCarId()==car.getCarId() || currentCar.getCarBrand()==null  
|| currentCar.getCarModel()==null || currentCar.getCarModelOption()==null){
return Response.status(404).build();
}
}
cList.add(car);
return Response.status(201).build();
}

//Update id in the list
@PUT
@Path("{id}/update")
@Produces(MediaType.APPLICATION_JSON)
public Response updateCar(Car car){
for(Car currentCar : cList){
if(currentCar.getCarId()==car.getCarId()){ 
cList.remove(currentCar);
cList.add(car);
return Response.status(200).build();
}
}
return Response.status(404).build();
}

//Delete and Id in the lists
@DELETE
@Path("{id}/delete")
public Response deleteCar(@PathParam("id") long id){
for(Car currentCar: cList) {
if(currentCar.getCarId() == id) {
cList.remove(currentCar);
return Response.status(200).build();
}  
}
return Response.status(404).build();
}

}
