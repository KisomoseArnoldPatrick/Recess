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
                                        <h4 class="card-title">Registered Pupils</h4>
                                       
                                        
                                        <div class="table-responsive">
                                           
                                            <table class="table table-hover mb-0">
        
                                                <thead>
                                                    <tr>
                                                        <th>Username</th>
                                                        <th>FirstName</th>
                                                        <th>Lastname</th>
                                                        <th>Email</th>
                                                        <th>DateofBirth</th>
                                                        <th>SchRegno</th>
                                                        <th>image</th>
                                                    </tr> 
                                                </thead>
                                                <tbody>
                                                @foreach($pupil as $pupils)
                                                    <tr>
                                                        <td>{{$pupils->Username}}</td>
                                                        <td>{{$pupils->fstname}}</td>
                                                        <td>{{$pupils->lstname}}</td>
                                                        <td>{{$pupils->email}}</td>
                                                        <td>{{$pupils->DOB}}</td>
                                                        <td>{{$pupils->Schregno}}</td>
                                                        <td>{{$pupils->image_file}}</td>
                                                        
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