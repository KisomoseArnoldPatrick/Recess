@extends('layouts/dash')
@section('content')
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box d-sm-flex align-items-center justify-content-between">
                                    

                                    <div class="page-title-right">
                                       
                                    </div>

                                </div>
                            </div>
                        </div>
                       
        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">All Registered Schools</h4>
                                       
                                        
                                        <div class="table-responsive">
                                           
                                            <table class="table table-hover mb-0">
        
                                                <thead>
                                                    <tr>
                                                        <th>Reg no</th>
                                                        <th>School Name</th>
                                                        <th>Email</th>
                                                        <th>District</th>
                                                        <th>Rep ID</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                @foreach($allschools as $school)
                                                    <tr>
                                                        <td>{{$school->regno}}</td>
                                                        <td>{{$school->schoolname}}</td>
                                                        <td>{{$school->email}}</td>
                                                        <td>{{$school->district}}</td>
                                                        <td>{{$school->repid}}</td>
                                                    </tr>
                                                    @endforeach
                                                   
                                                </tbody>
                                            </table>
                                        </div>
        
                                    </div>
                                </div>
                            </div>
                            
                            
                        </div>
                        <!-- end row -->
        
                        <div class="row">
                            
                            
                            <div class="col-lg-6">
                                
                            </div>
                        </div>
                        <!-- end row -->
                        
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card">
                                    
                                </div>
                            </div>
                        </div>
                        <!-- end row -->
                        
                  
                
               
                
         

@endsection